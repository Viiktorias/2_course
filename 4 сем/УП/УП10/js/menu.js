window.onload = function () {
    document.querySelectorAll('.dot').forEach(function (e) {
        e.addEventListener('mouseover', function () {
            this.style.backgroundColor = "#ffcc00";
        });
        if (e.className.match(/\bgreen\b/)) {
            e.addEventListener('mouseleave', function () {
                this.style.backgroundColor = "#00cc33";
            });
        } else {
            e.addEventListener('mouseleave', function () {
                this.style.backgroundColor = "#ff3333";
            });
        }
    });
};