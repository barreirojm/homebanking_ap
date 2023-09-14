Vue.createApp({
    data() {
        return {
            clientInfo: {},
            onlyActiveAccounts: [],
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
                    this.onlyActiveAccounts = this.clientInfo.accounts.filter(account => account.active == true);
                    console.log(this.onlyActiveAccounts);
                })
                .catch((error) => {
                    // handle error
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
        create: function () {
            axios.post('/api/clients/current/accounts')
                .then(response => window.location.reload())
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
        },
        /////////////////
        deleteSelectedAccount: function () {
           if (this.selectedAccount) {
                 const accountId = this.selectedAccount.id;
                 console.log('Id de la cuenta seleccionada:', accountId);
                 axios.patch(`/api/clients/current/accounts?id=${accountId}`)
                   .then(response => {
                   console.log(response.data);
                   this.clientInfo.accounts = this.clientInfo.accounts.filter(account => account.id !== accountId);
                   window.location.reload();
                   })
                   .catch(error => {
                     console.error(error);
                   });
            }

        }
    },


    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }

}).mount('#app')