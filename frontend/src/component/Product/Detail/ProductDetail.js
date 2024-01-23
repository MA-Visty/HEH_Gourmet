import React, {useState} from "react";
import axios from "axios";
import {LinkContainer} from "react-router-bootstrap";
import {Container, Row, Col, Button, Table, Image} from 'react-bootstrap';
import {Navigate, useParams} from "react-router-dom";
import ProductItemFavorite from "../Item/ProductItemFavorite";
import Error from "../../Error/Error";
import Loader from "../../Loader/Loader";
import {useAppContext} from "../../../store/AppContext";
import API_URL from "../../../apiConfig";
import Trash from "../../../assets/images/trash.svg";
import Edit from "../../../assets/images/edit.svg";
import ProductDetailTable from "./ProductDetailTable";
import ProductDetailNewTable from "./ProductDetailNewTable";

function Products() {
    const { state } = useAppContext();
    const [data, setData] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);
    const itemID = useParams().id;
    const [redirect, setRedirect] = useState(false)
    const [newData, setNewData] = useState(false)

    useState(() => {
        if (itemID !== "newProduct") {
            axios.get(`${API_URL}/api/product/${itemID}`)
                .then(function (reponse) {
                    setData(reponse.data);
                    setLoading(false);
                })
                .catch(function (error) {
                    setCrash(true);
                });
        } else {
            // TODO : Worker if not user
            if (state.user.role !== "worker") {
                setLoading(false);
                setNewData(true);
                setData({
                    ID: null,
                    name: "Miam 1",
                    description: "Miam Miam",
                    price: 25.0,
                    stock: 5,
                    image: Edit,
                    categoryID: 1
                });
            } else {
                setRedirect(true);
            }
        }
    });

    return (
        <Container style={{paddingTop: 15, paddingBottom: 15, background: "#FFF", minHeight: "100vh"}}>
            <Row>
                { redirect ?
                    <Navigate to="/Menu" />
                : isCrash ?
                    <Error/>
                : loading ?
					<Loader/>
                : <>
						<Col sm={2}>
							<LinkContainer to="/Menu">
								<Button as="input" type="reset" value="return"/>
							</LinkContainer>
						</Col>
						<Col sm={8}>
                            {newData ?
                                <ProductDetailNewTable data={data} />
                                :
                                <ProductDetailTable data={data}/>
                            }
						</Col>
						<Col sm={2} style={{filter: "invert(16%) sepia(80%) saturate(7434%) hue-rotate(358deg) brightness(104%) contrast(111%)"}}>
                            <Image style={{objectFit: 'contain', width: '100%', height: '250px', fill: "red"}}
                                   src={Trash}/>
						</Col>
					</>
                }
            </Row>
        </Container>
    );
}

export default Products;