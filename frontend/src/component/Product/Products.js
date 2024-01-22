import axios from "axios";
import { Row, Col, Container } from "react-bootstrap";
import React, {useEffect, useState} from "react";
import ProductItem from "../../component/Product/ProductItem";
import Error from "../../component/Error/Error";
import Loader from "../../component/Loader/Loader";
import EmptyData from "../../component/Loader/EmptyData";
import ProductItemFilter from "./ProductItemFilter";
import API_URL from "../../apiConfig";

function Products() {
    const [data, setData] = useState([]);
    const [dataFilter, setDataFilter] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {axios
            .get(`${API_URL}/api/products`)
            .then((response) => {
                setData(response.data);
                setDataFilter(response.data);
                setLoading(false);
            })
            .catch((error) => {
                setCrash(true);
            });
    }, []);

    return (
        <Container style={{paddingTop: 15, paddingBottom: 15, background: "#FFF", minHeight: "100vh"}}>
            {isCrash ?
                <Error />
            : loading ?
                <Loader/>
            :
                <>
                    <ProductItemFilter loading={loading} data={data} setDataFilter={setDataFilter} />
                    <Row sm={1} md={2} lg={3} xl={4} className="g-4 justify-content-md-center">
                        {dataFilter.length === 0 ?
                            <EmptyData/>
                        : dataFilter.map((item) => (
                            <Col sm>
                                <ProductItem product={item} class="bg-primary"/>
                            </Col>
                        ))}
                    </Row>
                </>
            }
        </Container>
    );
}

export default Products;