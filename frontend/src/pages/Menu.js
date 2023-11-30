import axios from "axios";
import {Alert, Form, FormGroup, Row, Spinner} from "react-bootstrap";
import React, {useEffect, useRef, useState} from "react";
import ProductItem from "../component/Product/ProductItem";
import Error from "../component/Error/Error";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";

function Menu() {
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
            <Search data={data}/>
            {isCrash ?
                <Error/> :
                loading ?
                    <Loader/> :
                    <Row sm={1} md={2} lg={3} xl={4} className="g-4 justify-content-md-center">
                        {dataFilter.length === 0 ?
                            "test":
                            dataFilter.map((item) => (
                                <Col sm><ProductItem data={data} product={item}/></Col>
                            ))}
                    </Row>
            }
        </Container>
    );
}

export function Loader() {
    return (
        <Row sm={1} md={2} lg={3} xl={4} className="g-4 justify-content-center">
            <Button variant="primary" disabled>
                <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true"/>
                {' '}
                Loading...
            </Button>
        </Row>
    );
}

export function Search({data, setDataFilter}) {
    const filter = useRef(null);

    return (
        <Form noValidate style={{marginBottom: "2rem"}}>
            <FormGroup className="d-flex justify-content-center">
                <Form.Control ref={filter} type="text" placeholder="Search" onChange={() => {
                    if (filter.current.value !== "") {
                        setDataFilter(
                            data.filter((product) =>
                                product.name
                                    .toLowerCase()
                                    .includes(filter.current.value.toLowerCase())
                            )
                        );
                    } else {
                        setDataFilter(data);
                    }
                }}/>
            </FormGroup>
        </Form>
    );
}

export default Menu;