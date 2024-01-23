import React, {useRef, useState} from "react";
import axios from "axios";
import {LinkContainer} from "react-router-bootstrap";
import {Container, Row, Col, Button, Table, Image, Stack} from 'react-bootstrap';
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
import Add from "../../../assets/images/add.svg";

function Products() {
    const { state } = useAppContext();
    const [data, setData] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);
    const itemID = useParams().id;
    const [redirect, setRedirect] = useState(false)
    const [editData, setEditData] = useState(false)
    const [newData, setNewData] = useState(false)
    const childRef = useRef();
    const [deleted, setDeleted] = useState(false)

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
                    name: null,
                    description: null,
                    price: null,
                    stock: null,
                    image: Edit,
                    categoryID: null
                });
            } else {
                setRedirect(true);
            }
        }
    });

    const handleEdit = () => {
        setEditData(true);
    }
    const handleDelete = async () => {
        const confirmation = window.confirm("Êtes-vous sûr de vouloir supprimer ce produit ?");
        if (confirmation) {
            try {
                const responseImage = await axios.delete(`${API_URL}/api/image?url=${encodeURIComponent(data.image)}`);
                if (responseImage.status == 204) {
                    try {
                        const response = await axios.delete(`${API_URL}/api/product/${data.ID}`);
                        if(response.status == 204) {
                            setDeleted(true);
                        }
                    } catch (error) {
                        console.log(error)
                    }
                }
            } catch (error) {
                console.log(error)
            }
        }
    }

    return (
        <Container style={{paddingTop: 15, paddingBottom: 15, background: "#FFF", minHeight: "100vh"}}>
            <Row>
                { redirect || deleted ?
                    <Navigate to="/Menu" />
                : isCrash ?
                    <Error/>
                : loading ?
					<Loader/>
                : <>
						<Col sm={2} style={{position: "relative"}}>
                            <Stack gap={3} style={{position: "fixed"}}>
                                <LinkContainer to="/Menu">
                                    <Button as="input" type="reset" value="Retour"/>
                                </LinkContainer>
                                {newData || editData ?
                                    <Button as="input" type="reset" value={newData ? "Ajouter" : "Valider"} onClick={() => childRef.current.handleRegister()}/>
                                :
                                    <>
                                        <Button as="input" type="reset" value="Modifier" onClick={handleEdit}/>
                                        <Button variant="outline-secondary"
                                                onClick={handleDelete}
                                                style={{
                                                    filter: "invert(16%) sepia(80%) saturate(7434%) hue-rotate(358deg) brightness(104%) contrast(111%)",
                                                    backgroundImage: `url(${Trash})`,
                                                    backgroundPosition: "center",
                                                    backgroundSize: "contain",
                                                    backgroundRepeat: "no-repeat",
                                                    width: "auto",
                                                    height: "40px"
                                                }}/>
                                    </>
                                }
                            </Stack>
						</Col>
						<Col sm={8}>
                            {newData || editData ?
                                <ProductDetailNewTable data={data} childRef={childRef} type={newData ? "create" : "update"} />
                                :
                                <ProductDetailTable data={data}/>
                            }
						</Col>
					</>
                }
            </Row>
        </Container>
    );
}

export default Products;