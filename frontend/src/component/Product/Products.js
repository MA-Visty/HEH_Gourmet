import axios from "axios";
import { Row, Col, Container } from "react-bootstrap";
import React, {useEffect, useState} from "react";
import ProductItem from "../../component/Product/ProductItem";
import Error from "../../component/Error/Error";
import Loader from "../../component/Loader/Loader";
import EmptyData from "../../component/Loader/EmptyData";
import ProductItemFilter from "./ProductItemFilter";

function Products() {
    const [data, setData] = useState([]);
    const [dataFilter, setDataFilter] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios
            .get("http://localhost:3000/api/product")
            .then((response) => {
                setData(response.data.products);
                setDataFilter(response.data.products);
                setLoading(false);
            })
            .catch((error) => {
                setCrash(true);
            });
    }, []);

    return (
        <Container style={{marginTop: 15}}>
            <ProductItemFilter data={data} setDataFilter={setDataFilter}/>
            <Row sm={1} md={2} lg={3} xl={4} className="g-4 justify-content-md-center">
                {isCrash ?
                    <Error />
                    : loading ?
                        <Loader/>
                    : dataFilter.length === 0 ?
                        <EmptyData/>
                    : dataFilter.map((item) => (
                        <Col sm>
                            <ProductItem data={data} product={item}/>
                        </Col>
                    ))
                }
            </Row>
        </Container>
    );
}

export default Products;