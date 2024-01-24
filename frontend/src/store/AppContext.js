import { createContext, useContext, useReducer } from "react";
import { login, logout } from "./UserContext";
import { loadCart, add2cart, remove2cart, removecart } from "./CartContext";
import { addfav, removefav} from "./FavoriteContext";

export const AppContext = createContext();
export const dispatchContext = createContext();

const initialState = { user: "", token: "", cart: [], quantity: 0, price: 0, favorite: [] };

function appContextReducer(state = initialState, action) {
    switch (action.type) {
        case "loadCart":
            return loadCart(state);
        case "add":
            return add2cart(state, action);
        case "remove":
            return remove2cart(state, action);
        case "removeAll":
            return removecart(state, action);

        case "addfav":
            return addfav(state, action);
        case "removefav":
            return removefav(state, action);

        case "login":
            let tempoState = login(state, action);
            return loadCart(tempoState, action);
        case "logout":
            return logout(state);
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