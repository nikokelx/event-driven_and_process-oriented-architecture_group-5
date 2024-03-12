<script>

    import { enhance } from "$app/forms";

    const startTheMachine = async () => {

        const url = "http://127.0.0.1:4000/machine/start";

        const response = await fetch(url, {
            method: "POST",
            mode: "no-cors",
            headers: {

            },
        });
    }

    const startProductionMachine = async () => {
        const url = "";
    }

</script>

<div class="w3-container">

    <button on:click={() => startKafka()} class="w3-button">Start Kafka!</button>

    <div class="w3-container w3-center w3-large">
        <h1>Welcome to CiRa!</h1>
    </div>
    
    <div class="w3-row">

        <div class="w3-third">

            <div class="w3-container w3-margin w3-padding w3-border">

                <div class="w3-center">
                    <h2>Machine 01</h2>
                </div>

                <button on:click={() => startTheMachine()} class="w3-button w3-hover-gray w3-aqua w3-block">Start Machine</button>

                <button on:click={() => startProductionMachine()} class="w3-button w3-hover-gray w3-aqua w3-block">Start Production</button>

            </div>

        </div>

        <div class="w3-twothird">

            <div class="w3-container w3-margin w3-padding w3-border">

                <h2>Logs</h2>

                <div class="w3-border w3-padding" style="height: 800px;">

                </div>

            </div>
        </div>

    </div>

</div>
