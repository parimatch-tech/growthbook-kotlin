// swift-tools-version:5.5
import PackageDescription

let package = Package(
    name: "GrowthBook",
    platforms: [
        .iOS(.v12)
    ],
    products: [
        .library(
            name: "GrowthBook",
            targets: ["GrowthBook"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "GrowthBook",
            url: "https://github.com/parimatch-tech/growthbook-kotlin/releases/download/0.9/GrowthBook.zip",
            checksum: "b3e24aa6ec1f83f4f3980ad1fe0046978491eff202fc748c6b417393fb066c13"
        ),
    ]
)
