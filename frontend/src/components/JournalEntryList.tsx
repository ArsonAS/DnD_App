import {Campaign} from "../models/Campaign";
import {formatDate, JournalEntry} from "../models/JournalEntry";
import {useNavigate} from "react-router-dom";
import {Button, Table} from "react-bootstrap";

interface Props {
    journalEntries: JournalEntry[];
}
export const JournalEntryList = ({journalEntries}: Props) => {
    const navigate = useNavigate();

    const journalEntryList = journalEntries.map(
        jE => {
            return <tr key={jE.id} className="align-items-baseline">
                <td>{formatDate(jE.entryDate)}</td>
                <td>{jE.entry}</td>
            </tr>
        }
    );

    return (
        <Table className="text-warning border border-warning align-items-baseline vh-100">
            <tbody>
                {journalEntryList}
            </tbody>
        </Table>
    );
}