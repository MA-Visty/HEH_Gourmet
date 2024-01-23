import React, {useImperativeHandle, useRef, useState} from "react";
import axios from "axios";
import API_URL from "../../../apiConfig";
import {Button, Container, Image, InputGroup, Table} from "react-bootstrap";
import Form from 'react-bootstrap/Form';
import {Navigate} from "react-router-dom";

function ProductDetailNewTable({data, childRef}) {
    const [dataCategory, setDataCategory] = useState([]);
    const [isCrash, setCrash] = useState(false);
    const [loading, setLoading] = useState(true);
    const [validated, setValidated] = useState(false);
    const [invalid, setInvalid] = useState({})
    const name = useRef(data.name);
    const description = useRef(data.description);
    const price = useRef(data.price);
    const stock = useRef(data.stock);
    const image = useRef(data.image);
    const [imageSrc, setImageSrc] = useState(null);
    const [imageUrl, setImageUrl] = useState(null);
    const categoryID = useRef(data.categoryID);
    const [sendValid, setSendValid] = useState(false);

    useState(() => {
        axios.get(`${API_URL}/api/categories`)
            .then(function (reponse) {
                setDataCategory(reponse.data);
                setLoading(false);
            })
            .catch(function (error) {
                setCrash(true);
            });
    });

    const handleImageChange = () => {
        const file = image.current.files[0];

        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setImageSrc(reader.result);
            };
            reader.readAsDataURL(file);
        } else {
            setImageSrc(null);
        }
    };

    useImperativeHandle(childRef, () => ({
        async handleRegister(event) {
            setInvalid({});
            if (image.current.value === "") {
                setInvalid((prevInvalid) => ({
                    ...prevInvalid,
                    image: `L'image est incorrecte`,
                }));
            }
            if (name.current.value === "") {
                setInvalid((prevInvalid) => ({
                    ...prevInvalid,
                    name: `Le nom est incorrect`,
                }));
            }
            if (description.current.value === "") {
                setInvalid((prevInvalid) => ({
                    ...prevInvalid,
                    description: `La description est incorrect`,
                }));
            }
            if (price.current.value === "" || price.current.value <= 0) {
                setInvalid((prevInvalid) => ({
                    ...prevInvalid,
                    price: `Le prix est incorrect`,
                }));
            }
            if (stock.current.value === "") {
                setInvalid((prevInvalid) => ({
                    ...prevInvalid,
                    stock: `Le stock est incorrect`,
                }));
            }
            if (
                categoryID.current.value === "Selectionné une catégorie" ||
                categoryID.current.value === ""
            ) {
                setInvalid((prevInvalid) => ({
                    ...prevInvalid,
                    category: `La catégorie est incorrecte`,
                }));
            }
            if (Object.keys(invalid).length !== 0) {
                setValidated(true);
                return;
            } else {
                try {
                    const formData = new FormData();
                    formData.append('image', image.current.files[0]);

                    const response = await axios
                        .post(`${API_URL}/api/image`, formData, {
                            headers: {
                                'Content-Type': 'multipart/form-data',
                            },
                        });
                    setImageUrl(response.data)
                    setValidated(false);

                    try {
                        const response = await axios
                            .post(`${API_URL}/api/product`, {
                                name: name.current.value,
                                description: description.current.value,
                                price: price.current.value,
                                stock: stock.current.value,
                                image: imageUrl.url,
                                categoryID: categoryID.current.value
                            });
                        setValidated(false);
                        if (response.status == 201) {
                            setSendValid(true);
                        }
                    } catch (error) {
                        console.log(error)
                    }
                } catch (error) {
                    console.log(error)
                }
                return;
            }
        }
    }));

    return (
        <Container style={{position: "relative"}}>
            {sendValid ? <Navigate to="/Menu" /> : <></>}
            <Image style={{objectFit: 'contain', width: '100%', height: '250px'}}
                   src={imageSrc !== null ? imageSrc : data.image}/>
            <Form noValidate validated={validated}>
                <Form.Group controlId="formFile" className="mb-3">
                    <Form.Label>Image</Form.Label>
                    <Form.Control
                        required
                        ref={image}
                        type="file"
                        onChange={handleImageChange} />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Nom</Form.Label>
                    <InputGroup hasValidation>
                        <Form.Control
                            required
                            ref={name}
                            type="text"
                            placeholder="Entrer le nom du produit"
                            defaultValue=""
                            isInvalid={!!invalid.name}
                        />
                        <Form.Control.Feedback type="invalid">
                            {invalid.name}
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>

                <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        required
                        ref={description}
                        type="text"
                        placeholder="Entrer la description du produit"
                        defaultValue=""
                        as="textarea"
                        rows={3} />
                </Form.Group>

                <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                    <Form.Label>Prix</Form.Label>
                    <InputGroup className="mb-3">
                        <Form.Control
                            required
                            ref={price}
                            type="number"
                            step="0.01"
                            placeholder="Entrer le prix du produit"
                            defaultValue=""
                            min="0"
                            aria-label="Recipient's username"
                            aria-describedby="basic-addon2"
                            isInvalid={!!invalid.price}
                        />
                        <Form.Control.Feedback type="invalid">
                            {invalid.price}
                        </Form.Control.Feedback>
                        <InputGroup.Text>€</InputGroup.Text>
                    </InputGroup>
                </Form.Group>

                <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                    <Form.Label>Stock</Form.Label>
                    <InputGroup className="mb-3">
                        <Form.Control
                            required
                            ref={stock}
                            type="number"
                            placeholder="Entrer le nombre de produit dans le stockage"
                            defaultValue=""
                            min="0"
                            aria-label="Recipient's username"
                            aria-describedby="basic-addon2"
                            isInvalid={!!invalid.stock}
                        />
                        <Form.Control.Feedback type="invalid">
                            {invalid.stock}
                        </Form.Control.Feedback>
                    </InputGroup>
                </Form.Group>

                <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
                    <Form.Label>Catégorie</Form.Label>
                    <Form.Select aria-label="Default select example" ref={categoryID}>
                        <option>Selectionné une catégorie</option>
                        {dataCategory.map((category) =>
                            <option value={category.ID}>{category.name}</option>
                        )}
                    </Form.Select>
                </Form.Group>
            </Form>
        </Container>
    );
}

export default ProductDetailNewTable;