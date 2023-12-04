import {useEffect, useRef, useState} from "react";
import { Form, FormGroup, Button, ButtonGroup, ButtonToolbar } from "react-bootstrap";

export function ProductItemFilter({data, setDataFilter}) {
    const filter = useRef(null);
    const [typeSelect, setTypeSelect] = useState("");
    useEffect(() => {handleSearch();}, [typeSelect]);
    const typeFilter = [
        "PC",
        "PC1",
        "PC2",
        "PC3",
        "PC4",
        "Mac",
        "Idea",
        "JSP"]

    const handleSearch = () => {
        if (filter.current.value !== "") {
            setTypeSelect("")
            setDataFilter(
                data.filter((product) =>
                    product.name
                        .toLowerCase()
                        .includes(filter.current.value.toLowerCase())
                )
            );
        } else if(typeSelect !== "") {
            setDataFilter(
                data.filter((product) =>
                    product.name
                        .toLowerCase()
                        .includes(typeSelect.toLowerCase())
                )
            );
        } else {
            setDataFilter(data);
        }
    };

    const hangleTypeSelect = (type) => {
        filter.current.value = ""
        setTypeSelect((prevType) => (prevType === type ? "" : type));
    };

    return (
        <ButtonToolbar className="justify-content-between" aria-label="Toolbar with Button groups" style={{marginBottom: "1rem"}}>
            <ButtonGroup aria-label="First group">
                {typeFilter.map((type) => (
                    <Button
                        variant={typeSelect === type ? 'success' : 'outline-secondary'}
                        onClick={() => hangleTypeSelect(type)}
                    >
                        {type}
                    </Button>
                ))}
            </ButtonGroup>

            <Form noValidate>
                <FormGroup className="d-flex justify-content-center">
                    <Form.Control ref={filter} type="text" placeholder="Search" onChange={handleSearch}/>
                </FormGroup>
            </Form>
        </ButtonToolbar>
    );
}

export default ProductItemFilter;