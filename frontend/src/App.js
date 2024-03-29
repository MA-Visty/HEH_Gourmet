import React from 'react';
import Header from "./component/Header/Header";
import {Route, Routes} from "react-router-dom";
import Home from "./component/Home/Home";
import Error from "./component/Error/Error";
import Products from "./component/Product/Products";
import ProductDetail from "./component/Product/Detail/ProductDetail";
import WorkSpace from "./component/WorkSpace/WorkSpace";

function App() {
    return (
        <>
            <div style={{zIndex: "-1", position: "fixed", left: "0", top: "0", right: "0", bottom: "0", background: "#062B16"}}></div>
            <Header />
            <Routes>
                <Route index element={<Home />}/>
                <Route path="menu" element={<Products />}/>
                <Route path="product/:id" element={<ProductDetail />}/>
                <Route path="workspace" element={<WorkSpace />} />
                <Route path="*" element={<Error type={"notFound"} />}/>
            </Routes>
        </>
    );
}

export default App;
