import {Campaign} from "../models/Campaign";
import {useNavigate} from "react-router-dom";
import {Button, Table} from "react-bootstrap";
import {getClientId} from "../security/authService";

interface Props {
    campaigns: Campaign[];
}
export const CampaignList = ({campaigns}: Props) => {
    const navigate = useNavigate();

    const handleclick = (id:number|undefined) => {
        if (id !== undefined) navigate("/campaignpage/" + id);
        else navigate("/clientpage/" + getClientId());
    }

    const campaignList = campaigns.map(
        campaign => {
            return <tr key={campaign.id} className="align-items-baseline">
                <td>{campaign.name}</td>
                <td>
                    <Button variant="warning" className="text-dark" onClick={() => handleclick(campaign.id)}>Voir page</Button>
                </td>
            </tr>
        }
    );
    return (
        <Table className="text-warning border border-warning align-items-baseline overflow-auto">
            <tbody>
                {campaignList}
            </tbody>
        </Table>
    );
}