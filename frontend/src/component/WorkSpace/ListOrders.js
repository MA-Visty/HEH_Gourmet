import React, {useEffect, useState} from "react";
import axios from "axios";
import API_URL from "../../apiConfig";
import {Col, Row} from "react-bootstrap";

function ListOrders({orders, setOrderSelct}) {

    return (
        <>
            {orders.map((order) => (
                <Row onClick={setOrderSelct(order.ID)} style={{border: "1px", borderColor: "#000"}}>
                    <Col>{order.orderDate}</Col>
                    <Col>{order.prepareDate}</Col>
                    <Col>{order.status}</Col>
                    <Col>{order.totalPrice}</Col>
                </Row>
            ))}
        </>
    );
}

export default ListOrders;