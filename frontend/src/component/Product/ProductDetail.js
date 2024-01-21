import React, {useState} from "react";
import axios from "axios";
import {LinkContainer} from "react-router-bootstrap";
import {Container, Row, Col, Button, Table, Image} from 'react-bootstrap';
import {useParams} from "react-router-dom";
import ProductItemFavorite from "./ProductItemFavorite";
import Error from "../../component/Error/Error";
import Loader from "../../component/Loader/Loader";
import EmptyData from "../../component/Loader/EmptyData";
import {useAppContext} from "../../store/AppContext";
import API_URL from "../../apiConfig";

function Products() {
    const { state } = useAppContext();
    const [data, setData] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);
    const itemID = useParams().id;

    useState(() =>
        axios.get(`${API_URL}/api/products/${itemID}`)
            .then(function (reponse) {
                setData(reponse.data);
                setLoading(false);
            })
            .catch(function (error) {
                setCrash(true);
            })
    );

    return (
        <Container style={{marginTop: 15}}>
            <Row sm={1} md={2} lg={3} xl={4} className="g-4 justify-content-md-center">
                {isCrash ?
                    <Error/>
                    : loading ?
					<Loader/>
					: <>
						<Col>
							<LinkContainer to="/Menu">
								<Button as="input" type="reset" value="return"/>
							</LinkContainer>
						</Col>
						<Col>
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
                                    <td>{data.type}</td>
                                </tr>
								<tr>
									<td>Description</td>
									<td>{data.description}
										<ol>{data.description.split('|').map((elem) => (
											<li>{elem}</li>
										))}
										</ol>
									</td>
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
						<Col>
                            {state.user !== "" ? <ProductItemFavorite product={data} /> : <></> }
						</Col>
					</>
                }
            </Row>
        </Container>
    );
}

export default Products;