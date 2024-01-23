import React, {useState} from "react";
import axios from "axios";
import {LinkContainer} from "react-router-bootstrap";
import {Container, Row, Col, Button, Table, Image} from 'react-bootstrap';
import {useParams} from "react-router-dom";
import ProductItemFavorite from "./ProductItemFavorite";
import Error from "../../component/Error/Error";
import Loader from "../../component/Loader/Loader";
import {useAppContext} from "../../store/AppContext";
import API_URL from "../../apiConfig";
import Trash from "../../assets/images/trash.svg";
import Edit from "../../assets/images/edit.svg";

function Products() {
    const { state } = useAppContext();
    const [data, setData] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);
    const itemID = useParams().id;

    useState(() => {
        if (itemID !== "newProduct") {
            axios.get(`${API_URL}/api/products/${itemID}`)
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
						<Col sm={8} style={{position: "relative"}}>
                            {state.user !== "" ? <ProductItemFavorite product={data} /> : <></> }
                            <ProductItemFavorite product={data} />
							<Image style={{objectFit: 'contain', width: '100%', height: '250px'}}
								   src={data.imageUrl}/>

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
                                    <td>{data.type !== null ? data.type.typeName : ""}</td>
                                </tr>
								<tr>
									<td>Description</td>
									<td>{data.description}</td>
								</tr>
                                <tr>
                                    <td>Ingredients</td>
                                    <td>
                                        <ol>{data.ingredients.map((elem) => (
                                            <li>{elem}</li>
                                        ))}
                                        </ol>
                                    </td>
                                </tr>
								</tbody>
							</Table>
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