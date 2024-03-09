<script>

    import { enhance } from "$app/forms";

    const startTheMachine = async () => {

        console.log("HALLO????")
        const url = "http://127.0.0.1:4000/machine/start";

        const response = await fetch(url, {
            method: "POST",
            mode: "no-cors",
            headers: {

            },
        });
    }

</script>

<div class="w3-container">

    <div class="w3-container w3-center w3-large">
        <h1>Welcome to CiRa!</h1>
    </div>
    
    <div class="w3-row">

        <div class="w3-third">

            <div class="w3-container w3-margin w3-padding w3-border">

                <div class="w3-center">
                    <h2>Machine 01</h2>
                </div>

                <form class="w3-margin" method="post" action="?/startMachine" use:enhance>
                    <button class="w3-button w3-hover-gray w3-aqua w3-block">Start Machine</button>
                </form>

                <form class="w3-margin" method="post" action="?/startProduction" use:enhance>
                    <button class="w3-button w3-hover-gray w3-aqua w3-block">Start production of Machine 01</button>
                </form>

                <button on:click={() => startTheMachine()} class="w3-button w3-hover-gray w3-aqua w3-block">Start Machine (Without button)</button>
            </div>

        </div>

        <div class="w3-twothird">

            <div class="w3-container w3-margin w3-padding w3-border">

                <h2>Logs</h2>

                <div class="w3-border" style="height: 800px;">
                    <p>hsad</p>
                </div>

            </div>
        </div>

    </div>

</div>
