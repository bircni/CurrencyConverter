![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84.svg?logo=android-studio&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?logo=java&logoColor=white)  
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]  
[![Gradle Build](https://github.com/bircni/CurrencyConverter/actions/workflows/android.yml/badge.svg)](https://github.com/bircni/CurrencyConverter/actions/workflows/android.yml)
[![Dependency Review](https://github.com/bircni/CurrencyConverter/actions/workflows/dependency-review.yml/badge.svg)](https://github.com/bircni/CurrencyConverter/actions/workflows/dependency-review.yml)  

# CurrencyConverter

## About The Project

 A small Android App to convert currencies.

- Supports multiple currencies and offline mode.
- Uses [EuropeanCentralBank API](www.ecb.europa.eu) to fetch the latest exchange rates.
- Uses [Paper](https://github.com/pilgr/Paper) to store the exchange rates locally.
- Currency rates are updated every 24 hours.
- Languages: German, English (default)

## Screenshots

<div>
<img src=".github/RM-graphics/converter-bright.png" alt="drawing" width="400"/>
<img src=".github/RM-graphics/converter-dark.png" alt="drawing" width="400"/>
</div>
<!--![bright](.github/RM-graphics/converter-bright.png)![dark](.github/RM-graphics/converter-dark.png) -->
<!-- ![bright](.github/RM-graphics/converter-bright-ls.jpg)![dark](.github/RM-graphics/converter-dark-ls.jpg)-->

### Prerequisites

- Android Studio
- Android SDK
- Android N (API 24) or higher

### Installation

1. Clone the repo

   ```sh
   git clone https://github.com/bircni/CurrencyConverter.git
   ```

2. Open in Android Studio
3. Run on emulator or device

<!-- CONTRIBUTING -->
## Contributing

If you want to contribute - open an issue or a pull request.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/CurrencyCool`)
3. Commit your Changes (`git commit -m 'added something new'`)
4. Push to the Branch (`git push origin feature/CurrencyCool`)
5. Open a Pull Request

[stars-shield]: https://img.shields.io/github/stars/bircni/CurrencyConverter.svg
[stars-url]: https://github.com/bircni/CurrencyConverter/stargazers
[issues-shield]: https://img.shields.io/github/issues/bircni/CurrencyConverter.svg
[issues-url]: https://github.com/bircni/CurrencyConverter/issues
[license-shield]: https://img.shields.io/github/license/bircni/CurrencyConverter.svg
[license-url]: https://github.com/bircni/CurrencyConverter/blob/main/LICENSE
