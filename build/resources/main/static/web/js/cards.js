Vue.createApp({
    data() {
        return {
            clientInfo: {},
            creditCards: [],
            debitCards: [],
            onlyActiveCards: [],
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    //get client ifo
                    this.clientInfo = response.data;
                    this.creditCards = this.clientInfo.cards.filter(card => card.type == "CREDIT" && card.active == true);
                    this.debitCards = this.clientInfo.cards.filter(card => card.type == "DEBIT" && card.active == true);
                    this.onlyActiveCards = this.clientInfo.cards.filter(card => card.active == true);
                    console.log('Todas las tarjetas:', this.clientInfo.cards);
                    console.log(this.creditCards)
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
        //////////////////////
        deleteSelectedCard: function () {
            if (this.selectedCard) {
                const cardId = this.selectedCard.id;
                console.log('Id de la tarjeta seleccionada:', cardId);
                axios.patch(`/api/clients/current/cards?id=${cardId}`)
                    .then(response => {

                        console.log(response.data);

                        //$('#staticBackdrop').modal('hide');
                        this.clientInfo.cards = this.clientInfo.cards.filter(card => card.id !== cardId);
                        window.location.reload();
                    })
                    .catch(error => {
                        // Maneja el error si es necesario
                        console.error(error);
                    });
            }
        },
        //////////////////////
        isCardExpired(card) {
                const currentDate = new Date();
                const expirationDate = new Date(card.thruDate);

                // Compara las fechas para determinar si la tarjeta está vencida
                return currentDate > expirationDate;
            }
        //////////////////////

    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
}).mount('#app')