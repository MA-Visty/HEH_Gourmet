import React, {useEffect, useState} from "react";
import {useAppContext} from "../../store/AppContext";
import {Navigate} from 'react-router-dom';
import OrderZone from "./OrderDetails";
import ListOrders from "./ListOrders";
import {Col, Container, Row} from "react-bootstrap";
import axios from "axios";
import API_URL from "../../apiConfig";

function WorkSpace() {
    const { state } = useAppContext();

    // TODO : Worker if not user
    /*
    if (state.user.role !== "worker") {
        return <Navigate to="/" />;
    }
     */

    const [data, setData] = useState([]);
    const [orderSelect, setOrderSelect] = useState(null);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {axios
        .get(`${API_URL}/api/products`)
        .then((response) => {
            setData(response.data);
            setLoading(false);
        })
        .catch((error) => {
            setCrash(true);
        });
    }, []);

    return (
        <Container style={{paddingTop: 15, paddingBottom: 15, background: "#FFF", minHeight: "100vh"}}>
            <Row style={{margin: 0, padding: 0}}>
                <Col sm={4}>
                    <ListOrders orders={data} setOrderSelct={setOrderSelect}/>
                </Col>
                <Col sm={8}>
                    <OrderZone orderSelect={data[orderSelect]}/>
                </Col>
            </Row>
        </Container>
    );
}

export default WorkSpace;