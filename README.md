# JOpenScan

JOpenScan is an alternative firmware for the [OpenScan](https://openscan.eu/), which is a low-cost,
high-quality 3D scanner that you can build yourself - completely open source and modular.

The main reason, why I decided to make this alternative firmware was, that I wanted
to use [Raspberry Pi Zero 2 W](https://www.raspberrypi.com/products/raspberry-pi-zero-2-w/) board
for my 3D scanner. This board has only 512MB of RAM, so it does not meet the minimum requirements for
the [original firmware](https://openscan-org.github.io/OpenScan-Doc/firmware/setup/).

This firmware offers the option to turn off the stepper motors. However, this feature is only available
when using [these PCBs](https://github.com/petrvlasak/jopenscan-pcb?tab=readme-ov-file#jopenscan---pi-shield-and-ringlight-pcbs).

## 🚧🚧🚧 This application is still under development 🚧🚧🚧

Here is a list of things that still remain to be completed.

- Capturing images with the Raspberry Pi Camera (only external camera switch is working for now)
- Live preview of the scanned object from the Raspberry Pi Camera
- User authentication and user management
- Working with projects and scanned images in them
- System Shutdown/Reboot
- Application release in the form of a deb package for easy installation in the Raspberry Pi OS

## Screenshots

Screenshots are located [here](docs/screenshots.md).
