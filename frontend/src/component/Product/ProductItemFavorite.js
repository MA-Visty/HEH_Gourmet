import React, {useState} from "react";
import { Badge } from 'react-bootstrap';

function ProductItemFavorite() {
    const [isFav, setFav] = useState(false);
    const [isEnter, setEnter] = useState(false);

    return (
        <Badge
            bg=""
            pill
            style={{
                position: 'absolute',
                top: 5,
                right: 5,
                zIndex: 1
            }}>
            <img
                src={isFav ? "/starComplet.svg" : "/star.svg"}
                alt="error"
                width={35}
                onClick={() => setFav((isFav ? false : true))}
                onMouseEnter={() => setEnter(true)}
                onMouseLeave={() => setEnter(false)}
                style={{
                    cursor: 'pointer',
                    transition: 'transform 0.1s ease-in-out',
                    transformOrigin: 'center',
                    transform: isEnter ? 'scale(1.2)' : 'scale(1)'
                }}/>
        </Badge>
    );
}

export default ProductItemFavorite;