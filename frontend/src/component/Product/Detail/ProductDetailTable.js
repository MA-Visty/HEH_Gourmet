import React from "react";
import ProductItemFavorite from "../Item/ProductItemFavorite";
import {Container, Image, Table} from "react-bootstrap";
import {useAppContext} from "../../../store/AppContext";

function ProductDetailTable({data}) {
    const { state } = useAppContext();

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
                    <td>{data.categoryID}</td>
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