import React, {useEffect, useRef, useState} from "react";
import axios from "axios";
import {LinkContainer} from "react-router-bootstrap";
import {Container, Row, Col, Button, Table, Image, Stack, Form, InputGroup} from 'react-bootstrap';
import {Navigate, useParams} from "react-router-dom";
import ProductItemFavorite from "../Item/ProductItemFavorite";
import Error from "../../Error/Error";
import Loader from "../../Loader/Loader";
import {useAppContext, useDispatchContext} from "../../../store/AppContext";
import API_URL from "../../../apiConfig";
import Trash from "../../../assets/images/trash.svg";
import Edit from "../../../assets/images/edit.svg";
import ProductDetailTable from "./ProductDetailTable";
import ProductDetailNewTable from "./ProductDetailNewTable";
import Add from "../../../assets/images/add.svg";
import ProductItemForm from "../Item/ProductItemForm";

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
    const { dispatch } = useDispatchContext();
    const quantity = useRef();
    const [invalid, setInvalid] = useState(false);

    const AddCart = (element) => {
        let num = quantity.current.value;
        if (num === "") {
            quantity.current.value = 1;
        } else if (!Number.isInteger(parseInt(num)) || num < 1 || Number.isNaN(parseInt(num))) {
            setInvalid(true);
            return;
        }
        dispatch({
            type: "add",
            product: data,
            quantity: Number(quantity.current.value),
            color: "red",
            size: "M",
        });
        quantity.current.value = 0
    };

    useEffect(() => {
        if (itemID !== "newProduct") {
            axios.get(`${API_URL}/api/product/${itemID}`)
                .then(function (response) {
                    setData(response.data);
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
    },[])

    const handleEdit = () => {
        setEditData(true);
    }
    const handleDelete = async () => {
        const confirmation = window.confirm("Êtes-vous sûr de vouloir supprimer ce produit ?");
        if (confirmation) {
            try {
                const responseImage = await axios.delete(`${API_URL}/api/image?url=${encodeURIComponent(data.image)}`);
                if (responseImage.status === 204) {
                    try {
                        const response = await axios.delete(`${API_URL}/api/product/${data.ID}`);
                        if(response.status === 204) {
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
        <Container style={{position:"relative", paddingTop: 15, paddingBottom: 15, background: "#FFF", minHeight: "100vh"}}>
            { redirect || deleted ?
                <Navigate to="/Menu" />
            : isCrash ?
                <Error/>
            : loading ?
                <Loader/>
            : <>
                {newData || editData ?
                    <ProductDetailNewTable data={data} childRef={childRef} type={newData ? "create" : "update"} />
                :
                    <ProductDetailTable data={data}/>
                }
                    <div style={{position: "absolute", top: 15, left: 15}}>
                        <LinkContainer to="/Menu">
                            <Button as="input" type="reset" value="Retour"/>
                        </LinkContainer>
                    </div>
                    <Stack direction="horizontal" gap={2}>
                        {newData || editData ?
                            <Button as="input" type="reset" value={newData ? "Ajouter" : "Valider"} onClick={() => childRef.current.handleRegister()}/>
                            :
                            <>
                                <Button as="input" type="reset" value="Modifier" onClick={handleEdit}/>
                                <InputGroup>
                                    <Form.Control ref={quantity} required isInvalid={invalid} type="number" min="0" max={data.stock} aria-label="Recipient's username" aria-describedby="basic-addon2" />
                                    <Button style={{width: "50%"}} variant="primary" id="button-addon2" onClick={AddCart}>Add</Button>
                                </InputGroup>
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
                </>
            }
        </Container>
    );
}

export default Products;