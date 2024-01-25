import axios from "axios";
import API_URL from "../apiConfig";
import products from "../component/Product/Products";

export function pushCart(state, action) {
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
        return x.product.ID === add_product.ID;
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

    return { user: state.user, cart: cart, quantity: new_quantity, price: new_price, favorite: state.favorite };
}

export function loadCart(state, action) {
    let tempoState = state

    action.response.data.map((product) => {
        tempoState = pushCart(tempoState, {
            product: product,
            quantity: product.quantity,
            color: "red",
            size: "M",
        });
    })

    return { user: tempoState.user, cart: tempoState.cart, quantity: tempoState.quantity, price: tempoState.price, favorite: tempoState.favorite };
}

export function add2cart(state, action) {
    const handleAdd = async (event) => {
        try {
            const response = await axios
                .post(`${API_URL}/api/cart/${state.user.id}?productID=${action.product.ID}&quantity=${action.quantity}`);
        } catch (error) {
            console.log(error)
        }
    }
    handleAdd()

    return pushCart(state, action);
}

export function remove2cart(state, action) {
    // set local variable with easier name
    let cart = structuredClone(state.cart);
    let new_price = structuredClone(state.price);
    let new_quantity = structuredClone(state.quantity);

    // TODO : remove product to cart via api using creditential stored in sessionStorage

    // update cart , quantity and price
    let new_cart = cart.filter((x) => {
        if (x.product.ID === action.id) {
            new_price = new_price - x.product.price * x.quantity;
            new_quantity = new_quantity - x.quantity;
            return false;
        } else {
            return true;
        }
    });

    const handleRemove = async (event) => {
        try {
            const response = await axios
                .delete(`${API_URL}/api/cart/${state.user.id}?productID=${action.id}`);
        } catch (error) {
            console.log(error)
        }
    }
    handleRemove()

    return { user: state.user, cart: new_cart, quantity: new_quantity, price: new_price, favorite: state.favorite };
}

export function removecart(state, action) {
    const handleRemove = async (user, product) => {
        try {
            const response = await axios
                .delete(`${API_URL}/api/cart/${user}?productID=${product}`);
        } catch (error) {
            console.log(error)
        }
    }
    state.cart.map((product) => {
        handleRemove(state.user.id, product.product.ID)
    })

    return { user: state.user, cart: [], quantity: 0, price: 0, favorite: state.favorite}
}