import React, {useState} from "react";
import axios from "axios";
import {LinkContainer} from "react-router-bootstrap";
import {Container, Row, Col, Button, Table, Image} from 'react-bootstrap';
import {useParams} from "react-router-dom";
import ProductItemFavorite from "../Item/ProductItemFavorite";
import Error from "../../Error/Error";
import Loader from "../../Loader/Loader";
import {useAppContext} from "../../../store/AppContext";
import API_URL from "../../../apiConfig";
import Trash from "../../../assets/images/trash.svg";
import Edit from "../../../assets/images/edit.svg";
import ProductDetailTable from "./ProductDetailTable";

function Products() {
    const [data, setData] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);
    const itemID = useParams().id;

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
            setLoading(false);
            setData({
                id: "newProduct",
                type: null,
                name: "Ajouter un produit",
                price: null,
                description: null,
                imageName: null,
                imageId: null,
                imageUrl: Edit,
                ingredients: []
            });
        }
    });

    return (
        <Container style={{paddingTop: 15, paddingBottom: 15, background: "#FFF", minHeight: "100vh"}}>
            <Row>
                {isCrash ?
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
                            <ProductDetailTable data={data}/>
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