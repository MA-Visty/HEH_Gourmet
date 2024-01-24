import axios from "axios";
import API_URL from "../apiConfig";

function pushFav(state, action) {
    state.favorite.push(action.product)

    return { user: state.user, cart: state.cart, quantity: state.quantity, price: state.price, favorite: state.favorite };
}

export function loadFav(state, action) {
    let tempoState = state

    action.response.fav.map((fav) => {
        tempoState = pushFav(tempoState, {
            product: fav.ID
        });
    })

    return { user: tempoState.user, cart: tempoState.cart, quantity: tempoState.quantity, price: tempoState.price, favorite: tempoState.favorite };
}

export function addfav(state, action) {
    const handleAdd = async (event) => {
        try {
            const response = await axios
                .post(`${API_URL}/api/user/${state.user.id}/fav/${action.product}`);
        } catch (error) {
            console.log(error)
        }
    }
    handleAdd()

    return pushFav(state, action);
}

export function removefav(state, action) {
    const indexToRemove = state.favorite.indexOf(action.product);

    if (indexToRemove !== -1) {
        state.favorite.splice(indexToRemove, 1);
    }

    const handleRemove = async (event) => {
        try {
            const response = await axios
                .delete(`${API_URL}/api/user/${state.user.id}/fav/${action.product}`);
        } catch (error) {
            console.log(error)
        }
    }
    handleRemove()

    return { user: state.user, cart: state.cart, quantity: state.quantity, price: state.price, favorite: state.favorite };
}