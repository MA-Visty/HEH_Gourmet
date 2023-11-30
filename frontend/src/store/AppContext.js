import { createContext, useContext, useReducer } from "react";
import axios from "axios";

// Having separate contexts for state and dispatch improves performance by preventing unnecessary re-renders.
export const AppContext = createContext();
export const dispatchContext = createContext();

const initialState = { cart: [], quantity: 0, price: 0 };

function appContextReducer(state = initialState, action) {
    switch (action.type) {
        case "add":
            return add2cart(state, action);
        case "remove":
            return remove2cart(state, action);
        case "login":
            return login(state, action);
        case "logout":
            return logout();
        default:
            return state;
    }
}

function login(state, action) {
    const response = axios
        .post("http://localhost:3000/api/login", {
            email: action.email,
            password: action.password,
        })
        .then((response) => {
            if (response.status !== 200) {
                throw new Error("login failed with status code: " + response.status);
            }
        })
        .catch((error) => {
            console.log(error);
        });
    let creditential = {
        accessToken: response.data.tokens.accessToken,
        refreshToken: response.data.tokens.refreshToken,
    };
    sessionStorage.setItem("creditential", JSON.stringify(creditential));
    return { ...state };
}

function logout() {
    axios
        .post("http://localhost:3000/api/auth/logout", {
            refreshToken: JSON.parse(sessionStorage.getItem("creditential"))
                .refreshToken,
        })
        .then((response) => {
            if (response.status !== 200) {
                throw new Error("logout failed with status code: " + response.status);
            }
        })
        .catch((error) => {
            console.log(error);
        });
    sessionStorage.removeItem("creditential");
    return { cart: [], quantity: 0, price: 0 };
}

function add2cart(state, action) {
    // set local variable with easier name
    let cart = structuredClone(state.cart);
    let add_product = action.product;
    let quantity = state.quantity;
    let add_quantity = action.quantity;
    let add_price = add_product.price;
    let price = state.price;
    let add_color = action.color;
    let add_size = action.size;

    // TODO : add product to cart via api using creditential stored in sessionStorage

    // check if quantity is valid
    if (add_quantity <= 0) {
        console.warn(
            "cannot add item to card with quantity smaller or equal than zero"
        );
        return state;
    }
    // find product index by id
    // if index is -1 by javascript standard it's not in the array
    const index = cart.findIndex((x) => {
        return x.product.id === add_product.id;
    });
    // add product to cart or update its quantity
    if (index === -1) {
        cart.push({
            product: add_product,
            quantity: add_quantity,
            color: add_color,
            size: add_size,
        });
    } else {
        cart[index].quantity = cart[index].quantity + add_quantity;
    }
    // update total quantity
    const new_quantity = add_quantity + quantity;
    // update total price
    const new_price = add_price * add_quantity + price;
    return { cart: cart, quantity: new_quantity, price: new_price };
}

function remove2cart(state, action) {
    // set local variable with easier name
    let cart = structuredClone(state.cart);
    let new_price = structuredClone(state.price);
    let new_quantity = structuredClone(state.quantity);

    // TODO : remove product to cart via api using creditential stored in sessionStorage

    // update cart , quantity and price
    let new_cart = cart.filter((x) => {
        if (x.product.id === action.id) {
            new_price = new_price - x.product.price * x.quantity;
            new_quantity = new_quantity - x.quantity;
            return false;
        } else {
            return true;
        }
    });

    return { cart: new_cart, quantity: new_quantity, price: new_price };
}

export const AppProvider = ({ children }) => {
    const [state, dispatch] = useReducer(appContextReducer, initialState);
    return (
        <AppContext.Provider value={{ state }}>
            <dispatchContext.Provider value={{ dispatch }}>
                {children}
            </dispatchContext.Provider>
        </AppContext.Provider>
    );
};

export const useAppContext = () => {
    const state = useContext(AppContext);
    if (state === undefined)
        throw new Error("useContext must be within AppProvider");
    return state;
};

export const useDispatchContext = () => {
    const dispatch = useContext(dispatchContext);
    if (dispatch === undefined)
        throw new Error("useContext must be within AppProvider");
    return dispatch;
};