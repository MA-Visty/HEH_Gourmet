import { createContext, useContext, useReducer } from "react";
import { login, logout } from "./UserContext";
import { add2cart, updatecart, remove2cart, removecart } from "./CartContext";

export const AppContext = createContext();
export const dispatchContext = createContext();

const initialState = { login: "", type: "", cart: [], quantity: 0, price: 0 };

function appContextReducer(state = initialState, action) {
    switch (action.type) {
        case "add":
            return add2cart(state, action);
        case "update":
            return updatecart(state, action);
        case "remove":
            return remove2cart(state, action);
        case "removeAll":
            return removecart(state, action);
        case "login":
            return login(state, action);
        case "logout":
            return logout();
        default:
            return state;
    }
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