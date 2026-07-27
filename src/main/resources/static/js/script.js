// Affichage / masquage du mot de passe (page de connexion)
const togglePassword = document.getElementById('togglePassword');
const passwordInput = document.getElementById('password');

if (togglePassword && passwordInput) {
    togglePassword.addEventListener('click', () => {
        const isPassword = passwordInput.getAttribute('type') === 'password';
        passwordInput.setAttribute('type', isPassword ? 'text' : 'password');

        const icone = togglePassword.querySelector('i');
        icone.classList.toggle('bi-eye');
        icone.classList.toggle('bi-eye-slash');
    });
}

// Bascule thème clair / sombre (dashboard admin et espace app)
const toggleTheme = document.getElementById('toggleTheme');
const themeActuel = localStorage.getItem('theme') || 'light';
document.documentElement.setAttribute('data-theme', themeActuel);

if (toggleTheme) {
    toggleTheme.addEventListener('click', () => {
        const nouveauTheme = document.documentElement.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
        document.documentElement.setAttribute('data-theme', nouveauTheme);
        localStorage.setItem('theme', nouveauTheme);
    });
}