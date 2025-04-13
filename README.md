# BMI Calculator App

This is a simple Body Mass Index (BMI) calculator built using Jetpack Compose. It allows users to input their weight and height in different units, calculate their BMI, and view health-related feedback based on their BMI score.

## Features

- Input weight in **pounds (Lbs)** or **kilograms (Kg)**
- Input height in **feet (Ft)** or **centimeters (Cm)**
- Automatic conversion of units for correct BMI calculation
- Display of BMI result with visual emphasis
- Feedback message based on BMI category (Underweight, Normal, Overweight, Obese)
- UI using Material3 components with segmented buttons and cards

## Setup Instructions

1. **Open in Android Studio:**
   - Launch Android Studio and select **"Open an Existing Project"**
   - Navigate to the project directory and open it

2. **Build the project:**
   - Android Studio will typically start building automatically. If not:
     - Go to **Build → Make Project**

3. **Run the app:**
   - Connect a physical Android device or start an emulator
   - Press the green **"Run"** button in the toolbar to launch the app

## Code Usage Examples

### Entering Input

Users can enter their weight in either **Lbs** or **Kg**, and height in **Cm** or **Ft**. The app automatically handles unit conversion before calculating the BMI.

### Calculating BMI

Tap the **"Calculate BMI"** button after entering valid values. The BMI is calculated using the standard formula:

BMI = weight(kg) / height²(m)

### Viewing Results

After calculation:
- The result is displayed in a card with a large font and category label
- A feedback message is shown according to the BMI category

## Short Demo Video

[Watch the BMI Calculator Demo](https://drive.google.com/file/d/1OGivX5uk03ZcOE0eq0K195nLXfVFzIVO/view?usp=sharing)
