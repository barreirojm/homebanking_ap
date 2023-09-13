Vue.createApp({
    data() {
        return {
            errorToats: null,
            errorMsg: null,
            cardType: "none",
            cardColor: "none",
        }
    },
    methods: {
        /////////////////////////////////////////
        deleteCard: function (cardId) {
                    axios.delete('/api/clients/current/cards/${id}')
                        .then(response => {
                            this.creditCards = this.clientInfo.cards.filter(card => card.id !== cardId);
                            this.debitCards = this.clientInfo.cards.filter(card => card.id !== cardId);
                            window.location.reload();
                        })
                        .catch(error => {
                            this.errorMsg = "Delete card failed";
                            this.errorToats.show();
                         });
        ////////////////////////////////////////
        }
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
    }
}).mount('#app')