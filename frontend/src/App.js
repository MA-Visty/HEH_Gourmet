import React, {useState} from 'react';
import Header from "./component/Header/Header";
import {Route, Routes} from "react-router-dom";
import Home from "./pages/Home";
import Error from "./component/Error/Error";
import Cart from "./component/Cart/Cart";
import Products from "./component/Product/Products";
import ProductDetail from "./component/Product/ProductDetail";

function App() {
    return (
        <>
            <Header />
            <Routes>
                <Route index element={<Home />}/>
                <Route path="menu" element={<Products />}/>
                <Route path="product/:id" element={<ProductDetail />}/>
                <Route path="*" element={<Error type={"notFound"} />}/>
            </Routes>
        </>
    );
}

export default App;
