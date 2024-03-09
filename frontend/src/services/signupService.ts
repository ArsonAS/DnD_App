import {User} from "../models/User";
import {AxiosResponse} from "axios";
import http from "../constants/http";
import {AUTH_PREFIX} from "../constants/apiPrefixes";

export const signup = async (user: User): Promise<AxiosResponse> => {
    return http.post(`${AUTH_PREFIX}/signup`, user);
};
