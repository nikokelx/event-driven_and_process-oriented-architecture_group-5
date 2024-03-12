import type {Actions} from "@sveltejs/kit";
import { Kafka } from "kafkajs";

export const load = async () => {
    /*
    const kafka = new Kafka({
        brokers: [`kafka:9092`],
        clientId: 'frontend',
    });

    const topic = "customLog";
    const consumer = kafka.consumer({groupId: "groupId"});

    await consumer.connect();
    await consumer.subscribe({topic});

    await consumer.run({
        eachMessage: async ({topic, partition, message}) => {
            const event = message.value.toString('utf-8');
            console.log(event);
        },
    });

     */

};

export const actions: Actions = {

    startMachine: async () => {

        const url = "http://machine-01:4000/machine/start";

        const response = await fetch(url, {
            method: "POST",
            mode: "no-cors",
            headers: {

            },
        });
    },
};