import React, {useEffect, useState} from "react";
import axios from "axios";
import API_URL from "../../apiConfig";
import {Col, Row} from "react-bootstrap";

function ListOrders({orders, setOrderSelct}) {


    return (
        <>
            {orders.map((order) => (
                <Row onClick={setOrderSelct(order.id)} style={{border: "1px", borderColor: "#000"}}>
                    <Col>order.state</Col>
                    <Col>order.userId</Col>
                    <Col>order.price</Col>
                    <Col>order.date</Col>
                </Row>
            ))}
        </>
    );
}

export default ListOrders;