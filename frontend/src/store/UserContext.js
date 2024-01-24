export function login(state, action) {
    return { user: action.response.user.data, cart: state.cart, quantity: state.quantity, price: state.price, favorite: state.favorite };
}

export function logout(state) {
    return { user: "", cart: [], quantity: 0, price: 0, favorite: [] };
}