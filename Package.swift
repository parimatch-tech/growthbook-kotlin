// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "GrowthBookLight",
    platforms: [
        .iOS(.v12)
    ],
    products: [
        .library(
            name: "GrowthBookLight",
            targets: ["GrowthBookLight"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "GrowthBookLight",
            url: "https://github.com/parimatch-tech/growthbook-kotlin/raw/Poc/RemoveNetworkLayer/GrowthBookLight.xcframework.zip"
        ),
    ]
)
