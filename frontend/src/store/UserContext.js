import axios from "axios";

export function login(state, action) {
    //sessionStorage.setItem("creditential", JSON.stringify(creditential));
    return { user: action.response.user, token: action.response.tokens, cart: state.cart, quantity: state.quantity, price: state.price, favorite: state.favorite };
}

export function logout(state) {
    axios
        .post("http://localhost:3000/api/auth/logout", {
            refreshToken: state.token.refreshToken,
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
    return { user: "", token: "", cart: [], quantity: 0, price: 0, favorite: [] };
}