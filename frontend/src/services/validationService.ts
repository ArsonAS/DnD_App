export const validateEmail = (email: string): boolean => {
    return (
        email !== ""
        && /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/i.test(email)
        && email.endsWith(".com")
    );
};

export const validatePassword = (password: string): boolean => {
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasDigit = /[0-9]/.test(password);

    return (password.length >= minLength && hasUpperCase && hasLowerCase && hasDigit);
};

export const validatePasswordConfirmation = (password: string, passwordConfirmation: string): boolean => {
    return password === passwordConfirmation;
};

