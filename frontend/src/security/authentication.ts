export interface LoginRequest {
    username: string;
    password: string;
}
export interface TimedJwt {
    jwt: string;
    expiration: number;
}
export interface DecodedJwt {
    sub: string;
    iat: number;
    exp: number;
    id: string;
    email: string;
    iss: string;
}