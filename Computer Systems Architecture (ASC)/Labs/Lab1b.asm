; z=(a*3+b*b*5)/(a*a+a*b)-a-b
ASSUME cs: code, ds:data

data SEGMENT

a db 4 ; try a = 4 and b = 2
b db 2
rez dw ?

data ENDS

code SEGMENT

start:
mov AX, data
mov DS, AX

mov AL, a
imul b
; AX = a*b
mov CX, AX
; CX = a*b

mov AL, a
imul a
; AX = a*a

add AX, CX
; AX = a*a+a*b
mov CX, AX

mov AL, 5
imul b
imul b
; AX = b*b*5

mov BX, AX
; BX = b*b*5

mov AL, 3
imul a
; AX = a*3

add AX, BX
; AX = a*3+b*b*5
cwd
idiv CX

;AX = (a*3+b*b*5)/(a*a+a*b)
mov AH, 0

mov BL, a
sub AX, BX
mov BL, b
sub AX, BX

mov rez, AX
mov AX, 4C00h
int 21h


code ENDS
END start




