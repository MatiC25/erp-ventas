/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/main/frontend/**/*.{html,js,ts,jsx,tsx}", // Archivos de frontend
        "./src/main/java/**/*.java"                       // Tus clases Java (¡Vital!)
    ],
    theme: {
        extend: {
            // Aquí puedes poner tus colores personalizados si quieres
            colors: {
                'mi-oscuro': '#1e1e2d',
            }
        },
    },
    plugins: [],
}