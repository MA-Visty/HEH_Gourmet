import React, {useState} from "react";
import axios from "axios";
import Container from 'react-bootstrap/Container';
import Error from "../Error/Error";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import ProductItem from "./ProductItem";
import Button from 'react-bootstrap/Button';
import Spinner from 'react-bootstrap/Spinner';

function Products() {
    const [data, setData] = useState([]);
    const [isLoad, setLoad] = useState(false);
    const [isCrash, setCrash] = useState(false);

    useState(() =>
        axios.get('http://localhost:3000/api/product')
            .then(function (reponse) {
                setData(reponse.data.products);
                setLoad(true);
            })
            .catch(function (error) {
                setCrash(true);
            })
    );

    return (
        <Container style={{marginTop: 15}}>
            {isCrash ?
                <Error/> :
                isLoad ?
                    <Row sm={1} md={2} lg={3} xl={4} className="justify-content-md-center">
                        {data.map((item) => (
                            <Col sm><ProductItem data={data} item={item}/></Col>
                        ))}
                    </Row> :
                    <Button variant="primary" disabled>
                        <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true"/>
                        {' '}
                        Loading...
                    </Button>
            }
        </Container>
    );
}

export default Products;