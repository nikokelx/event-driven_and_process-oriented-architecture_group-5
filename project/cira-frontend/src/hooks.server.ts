export async function handle({event, resolve}) {
    console.log("HOOOOK");
    return await resolve(event);
}