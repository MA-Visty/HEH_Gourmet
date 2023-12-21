import axios from "axios";

export function login(state, action) {
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

export function logout() {
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