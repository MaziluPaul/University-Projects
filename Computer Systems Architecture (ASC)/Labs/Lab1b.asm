; z=(a*3+b*b*5)/(a*a+a*b)-a-b
ASSUME cs: code, ds:data

data SEGMENT

a db 4
b db 2
rez dw ?

data ENDS

code SEGMENT

start:
mov AX, data
mov DS, AX

mov AL, 3
mul a
;AX = a*3

mov BX,AX
mov AL, 5
mul b
mul b
;AX = b*b*5

add AX,BX
;AX = a*3 + b*b*5 -works

mov CX,AX
mov AL, a
mul a
; AX = a*a

mov BX,AX
mov AL,a
mul b
; AX = a*b

add AX,BX
; AX = a*b + a*a -works

mov BX,AX
mov AX,CX
cwd
idiv BX

mov rez, AX
mov AX, 4C00h
int 21h


code ENDS
END start




