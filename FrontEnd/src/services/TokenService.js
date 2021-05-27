export default class TokenService {

    tokenIdentifier = "LASSE_FRONTEND_TOKEN"

    hasToken() {
        return localStorage.getItem(this.tokenIdentifier) !== null
    }

    setToken(token) {
        localStorage.setItem(this.tokenIdentifier, token)
    }

    getToken() {
        return localStorage.getItem(this.tokenIdentifier)
    }

    deleteToken() {
        localStorage.removeItem(this.tokenIdentifier)
    }
}