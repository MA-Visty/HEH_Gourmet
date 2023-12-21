export function add2cart(state, action) {
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
    return { user: state.user, token: state.token, cart: cart, quantity: new_quantity, price: new_price, favorite: state.favorite };
}

export function updatecart(state, action) {
    return { user: state.user, token: state.token, cart: state.cart, quantity: state.quantity, price: state.price, favorite: state.favorite };
}

export function remove2cart(state, action) {
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

    return { user: state.user, token: state.token, cart: new_cart, quantity: new_quantity, price: new_price, favorite: state.favorite };
}

export function removecart(state, action) {
    return { user: state.user, token: state.token, cart: [], quantity: 0, price: 0, favorite: state.favorite}
}