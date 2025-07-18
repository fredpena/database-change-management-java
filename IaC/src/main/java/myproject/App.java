package myproject;

import com.pulumi.Pulumi;
import com.pulumi.digitalocean.Droplet;
import com.pulumi.digitalocean.DropletArgs;
import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String fingerprint = dotenv.get("FINGERPRINT");

        Pulumi.run(ctx -> {
            // Create a new Droplet in nyc1
            Droplet droplet = new Droplet("web", DropletArgs.builder()
                    .image("debian-12-x64")
                    .name("iac-jconf-2025")
                    .region("nyc1")
                    .size("s-1vcpu-2gb")
                    .sshKeys(fingerprint)
                    .build());

            ctx.export("Your Droplet application is named:", droplet.name());
            ctx.export("The public IP address is: ", droplet.ipv4Address());


        });
    }
}
