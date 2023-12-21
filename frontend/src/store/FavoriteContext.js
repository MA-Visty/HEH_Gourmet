export function addfav(state, action) {
    state.favorite.push(action.product)

    return { ...state };
}

export function removefav(state, action) {
    const indexToRemove = state.favorite.indexOf(action.product);

    if (indexToRemove !== -1) {
        state.favorite.splice(indexToRemove, 1);
    }

    return { ...state };
}