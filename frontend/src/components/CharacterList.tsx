import {Character} from "../models/Character";
import {Button, Table} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {getClientId} from "../security/authService";


interface Props {
    characters: Character[];
}
export const CharacterList = ({characters}: Props) => {
    const navigate = useNavigate();

    const handleclick = (id:number|undefined) => {
        if (id !== undefined) navigate("/characterpage/" + id);
        else navigate("/clientpage/" + getClientId());
    }

    const characterList = characters.map(
        character => {
            return <tr key={character.id} className="align-items-baseline">
                <td>{character.name}</td>
                <td>Niveau: {character.level}</td>
                <td>Classe: {character.classe}</td>
                <td>
                    <Button variant="warning" className="text-dark" onClick={() => handleclick(character.id)}>Voir page</Button>
                </td>
            </tr>
        }
    );


    return (
        <Table className="text-warning border border-warning align-items-baseline">
            {characterList}
        </Table>
    );

}