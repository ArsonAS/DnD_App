import http from "../constants/http";
import {AxiosResponse} from "axios";
import {Client} from "../models/Client";
import {CLIENT_PREFIX} from "../constants/apiPrefixes";


export const getClientById = async (id: number): Promise<AxiosResponse<Client>> => {
    return http.get<Client>(`${CLIENT_PREFIX}/${id}`);
};