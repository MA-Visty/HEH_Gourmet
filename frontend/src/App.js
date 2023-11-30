import React, {useState} from 'react';
import Header from "./component/Header/Header";
import {Route, Routes} from "react-router-dom";
import Home from "./pages/Home";
import Error from "./component/Error/Error";
import Cart from "./component/Cart/Cart";
import Menu from "./pages/Menu";

function App() {
    const [show, setShow] = useState(false);
    const hideCart = () => setShow(false);
    const showCart = () => setShow(true);

    return (
        <>
            {show && <Cart hide={hideCart}></Cart>}
            <Header show={showCart}/>
            <Routes>
                <Route index element={<Home/>}/>
                <Route path="menu" element={<Menu/>}/>
                <Route path="product/:id" element={<></>}/>
                <Route path="*" element={<Error type={"notFound"} />}/>
            </Routes>
        </>
    );
}

export default App;
