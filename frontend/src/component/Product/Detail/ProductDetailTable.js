import React, {useEffect, useState} from "react";
import ProductItemFavorite from "../Item/ProductItemFavorite";
import {Container, Image, Table} from "react-bootstrap";
import {useAppContext} from "../../../store/AppContext";
import axios from "axios";
import API_URL from "../../../apiConfig";
import Loader from "../../Loader/Loader";

function ProductDetailTable({data}) {
    const { state } = useAppContext();
    const [loading, setLoading] = useState(true);
    const [dataCategory, setDataCategory] = useState(false);
    const [isCrash, setCrash] = useState(false);

    useEffect(() => {
        axios.get(`${API_URL}/api/category/${data.ID}`)
            .then(function (response) {
                setDataCategory(response.data);
                setLoading(false);
            })
            .catch(function (error) {
                setCrash(true);
            });
    }, []);

    return (
        <Container style={{position: "relative"}}>
            {state.user !== "" ? <ProductItemFavorite product={data} /> : <></> }
            <Image style={{objectFit: 'contain', width: '100%', height: '250px'}}
                   src={data.image}/>

            <Table striped bordered size="md">
                <tbody>
                <tr>
                    <td>Nom</td>
                    <td>{data.name}</td>
                </tr>
                <tr>
                    <td>Prix</td>
                    <td>{data.price} €</td>
                </tr>
                <tr>
                    <td>Type</td>
                    <td>{ dataCategory ? dataCategory.name : <Loader/>}</td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td>{data.description}</td>
                </tr>
                </tbody>
            </Table>
        </Container>
    );
}

export default ProductDetailTable;